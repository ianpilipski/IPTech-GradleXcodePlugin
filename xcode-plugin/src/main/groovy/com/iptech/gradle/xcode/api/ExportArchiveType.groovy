package com.iptech.gradle.xcode.api

import com.iptech.gradle.xcode.internal.DistributionSummaryPlist
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.provider.Property

abstract class ExportArchiveType implements ExportArchiveSpec, TestFlightSpec {
    final String name

    @Input @Optional abstract Property<Boolean> getValidate()
    @Input @Optional abstract Property<Boolean> getUpload()

    ExportArchiveType(String name) {
        this.name = name
        appType.convention('ios')
        appFile.convention(getExportPath().map {
            File ff = it.file('DistributionSummary.plist').asFile
            if(ff.exists()) {
                def ds = new DistributionSummaryPlist(ff)
                return it.file(ds.appName)
            } else {
                throw new Exception("Could not find DistributionSummary.plist")
            }
        })
    }
}
