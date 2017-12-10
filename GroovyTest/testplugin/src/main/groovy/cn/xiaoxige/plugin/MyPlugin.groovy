package cn.xiaoxige.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Created by zhuxiaoan on 2017/12/10.
 */

public class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println('---------------------')
        println('--------begin--------')
        println('---------------------')
    }
}
