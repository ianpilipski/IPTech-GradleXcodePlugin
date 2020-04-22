package com.iptech.gradle.xcode

import com.iptech.gradle.xcode.tasks.XcodeBuild
import org.gradle.api.Plugin
import org.gradle.api.Project

class XcodePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('xcode', XcodeExtension.class, project)
    }
}
