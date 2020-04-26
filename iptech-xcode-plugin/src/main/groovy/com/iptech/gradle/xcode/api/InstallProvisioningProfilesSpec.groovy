package com.iptech.gradle.xcode.api

import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles

interface InstallProvisioningProfilesSpec {
    @InputFiles Property<FileCollection> getProvisioningProfiles()
}