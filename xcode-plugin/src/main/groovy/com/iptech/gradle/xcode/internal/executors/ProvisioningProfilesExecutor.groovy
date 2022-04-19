package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.ProvisioningProfilesSpec
import org.gradle.api.GradleException
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DeleteSpec
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.tasks.WorkResult

import javax.inject.Inject
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Matcher

class ProvisioningProfilesExecutor {
    FileOperations fileOperations

    @Inject
    ProvisioningProfilesExecutor(FileOperations fileOperations) {
        this.fileOperations = fileOperations
    }

    WorkResult execInstall(ProvisioningProfilesSpec spec) {
        Path intoDir = getProvisioningProfileDestDirectory()

        return fileOperations.copy { CopySpec cs ->
            spec.provisioningProfiles.get().each {
                if (!it.exists()) throw new FileNotFoundException(it.absolutePath)

                String uuid = getUUID(it)
                cs.from it
                cs.rename it.name, "${uuid}.mobileprovision"

                println "Installing Provisioning Profile: ${it.name} (${uuid})"
            }

            cs.into intoDir
        }
    }

    WorkResult execUnInstall(ProvisioningProfilesSpec spec) {
        Path intoDir = getProvisioningProfileDestDirectory()

        return fileOperations.delete { DeleteSpec ds ->
            spec.provisioningProfiles.get().each {
                if (!it.exists()) throw new FileNotFoundException(it.absolutePath)

                String uuid = getUUID(it)

                File fileToDelete = intoDir.resolve("${uuid}.mobileprovision").toFile()
                if(fileToDelete.exists()) {
                    ds.delete(fileToDelete)
                    println "Uninstalling Provisioning Profile: ${it.name} (${uuid})"
                }
            }
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
