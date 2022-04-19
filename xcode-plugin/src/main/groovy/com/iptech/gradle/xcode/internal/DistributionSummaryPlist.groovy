package com.iptech.gradle.xcode.internal

import com.dd.plist.*

class DistributionSummaryPlist {
    File plistFile

    DistributionSummaryPlist(File plistFile) {
        this.plistFile = plistFile
    }

    String getAppName() {
        try {
            NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(plistFile)
            return rootDict.allKeys()[0]
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}