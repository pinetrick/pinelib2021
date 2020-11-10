package com.blueberrysolution.pinelib21.view.app_update

import com.blueberrysolution.pinelib21.basic_class_ext.helper_functions.i

object AppUpdate {
    var downloadLink: String = "";
    var version: String = "";
    var note: String = "";
    var file_size_mb: Double = 0.0;

    fun update(downloadLink: String, version: String, file_size_mb: Double = 0.0, note: String = "") {
        this.downloadLink = downloadLink;
        this.version = version;
        this.note = note;
        this.file_size_mb = file_size_mb;

        i(AppUpdateActivity::class).show();
    }
}
