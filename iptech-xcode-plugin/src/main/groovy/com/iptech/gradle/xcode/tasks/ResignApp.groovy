package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ResignAppSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

abstract class ResignApp extends DefaultTask implements ResignAppSpec {

    @TaskAction
    ExecResult execute() {
        return project.xcode.resignApp(this)
    }
}
