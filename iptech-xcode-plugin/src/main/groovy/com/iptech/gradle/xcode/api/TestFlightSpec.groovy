package com.iptech.gradle.xcode.api

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

interface TestFlightSpec {
    @InputFile RegularFileProperty getAppFile()
    @Input Property<String> getAppType()
    @Input Property<String> getUserName()
    @Input Property<String> getPassword()
}