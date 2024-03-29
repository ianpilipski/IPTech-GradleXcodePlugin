package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ProvisioningProfilesSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.WorkResult

abstract class InstallProvisioningProfiles extends DefaultTask implements ProvisioningProfilesSpec {
    InstallProvisioningProfiles() {
        super()
        onlyIf { provisioningProfiles.isPresent() }
    }

    @TaskAction
    WorkResult execute() {
        project.xcode.installProvisioningProfiles(this)
    }
}
