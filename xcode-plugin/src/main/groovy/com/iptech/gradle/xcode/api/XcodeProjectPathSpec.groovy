package com.iptech.gradle.xcode.api

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional

interface XcodeProjectPathSpec {
    @InputDirectory DirectoryProperty getProjectPath()
}
