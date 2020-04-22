package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

abstract class XcodeBuild extends DefaultTask implements XcodeBuildSpec {
    @TaskAction
    protected ExecResult execute() {
        return project.xcode.xcodeBuild(this)
    }
}
