package com.iptech.gradle.xcode.api

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

interface ResignAppSpec {
    @InputFile RegularFileProperty getAppPath()
    @InputFile @Optional RegularFileProperty getEntitlementsFile()
    @Input Property<String> getSigningCertName()
}