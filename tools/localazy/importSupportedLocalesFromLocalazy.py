#!/usr/bin/env python3

import os
import subprocess


def getLocalesFromLocalazy():
    command = subprocess.run(
        ["localazy languages --read-key a7876306080832595063-aa37154bb3772f6146890fca868d155b2228b492c56c91f67abdcdfb74d6142d --csv"],
        shell=True,
        capture_output=True,
        text=True,
    )
    data = command.stdout
    result = []
    for line in data.split("\n"):
        if line:
            line = line.split(",")
            if (line[6] == "true"):
                result.append(line[0])
    return sorted(result)


def normalizeForResourceConfigurations(locale):
    match locale:
        case "id":
            return "in"
        case "zh_TW#Hant":
            return "zh-rTW"
        case "pt_BR":
            return "pt-rBR"
        case "zh#Hans":
            return "zh-rCN"
        case _:
            return locale


def normalizeForLocalConfig(locale):
    match locale:
        case "id":
            return "in"
        case "zh_TW#Hant":
            return "zh-TW"
        case "zh#Hans":
            return "zh-CN"
        case _:
            return locale


def generateLocaleFile(locales, file):
    with open("plugins/src/main/kotlin/extension/locales.kt", "w") as f:
        f.write("// File generated by " + file + ", do not edit\n\n")
        f.write("package extension\n\n")
        f.write("val locales = setOf(\n")
        for locale in locales:
            f.write("    \"" + normalizeForResourceConfigurations(locale) + "\",\n")
        f.write(")\n")


def generateLocalesConfigFile(locales, file):
    with open("app/src/main/res/xml/locales_config.xml", "w") as f:
        f.write("<!-- File generated by " + file + ", do not edit -->\n")
        f.write('<locale-config xmlns:android="http://schemas.android.com/apk/res/android">\n')
        for locale in locales:
            f.write("    <locale android:name=\"" + normalizeForLocalConfig(locale) + "\"/>\n")
        f.write("</locale-config>\n")


def main():
    file = os.path.basename(__file__)
    locales = getLocalesFromLocalazy()
    generateLocaleFile(locales, file)
    generateLocalesConfigFile(locales, file)


if __name__ == "__main__":
    main()