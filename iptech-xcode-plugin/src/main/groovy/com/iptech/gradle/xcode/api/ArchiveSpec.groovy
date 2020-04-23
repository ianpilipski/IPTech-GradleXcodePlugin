package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory

interface ArchiveSpec extends XcodeProjectPathSpec {
    @Input Property<String> getConfiguration()
    @Input Property<String> getScheme()
    @Internal DirectoryProperty getDerivedDataPath()
    @OutputDirectory @Optional DirectoryProperty getArchivePath()

    @Input @Optional Property<String> getCODE_SIGN_IDENTITY()
    @Input @Optional Property<String> getCODE_SIGN_STYLE()
    @Input @Optional Property<String> getDEVELOPMENT_TEAM()
    @Input @Optional Property<String> getPROVISIONING_PROFILE_SPECIFIER()
    @Input @Optional Property<String> getCODE_SIGNING_REQUIRED()
    @Input @Optional Property<String> getCODE_SIGNING_ALLOWED()

    @Input @Optional ListProperty<String> getAdditionalArguments()
}