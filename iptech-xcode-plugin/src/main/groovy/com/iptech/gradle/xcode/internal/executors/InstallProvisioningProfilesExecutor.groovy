package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.InstallProvisioningProfilesSpec
import org.gradle.api.GradleException
import org.gradle.api.file.CopySpec
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.tasks.WorkResult

import javax.inject.Inject
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Matcher

class InstallProvisioningProfilesExecutor {
    FileOperations fileOperations

    @Inject
    InstallProvisioningProfilesExecutor(FileOperations fileOperations) {
        this.fileOperations = fileOperations
    }

    WorkResult exec(InstallProvisioningProfilesSpec spec) {
        Path intoDir = getProvisioningProfileDestDirectory()

        return fileOperations.copy { CopySpec cs ->
            spec.provisioningProfiles.get().each {
                if (!it.exists()) throw new FileNotFoundException(it.absolutePath)

                String uuid = getUUID(it)
                cs.from it
                cs.rename it.name, "${uuid}.mobileprovision"
            }

            cs.into intoDir
        }
    }

    static Path getProvisioningProfileDestDirectory() {
        return Paths.get(System.getProperty('user.home'), 'Library', 'MobileDevice' , 'Provisioning Profiles')
    }

    static String getUUID(File provisioningProfile) {
        Matcher m = provisioningProfile.text =~ /(?sm)<key>UUID<\/key>.+<string>(.+)<\/string>/
        if(m) {
            return m[0][1]
        }
        throw new GradleException("could not retrieve UUID from provisioning profile")
    }
}
