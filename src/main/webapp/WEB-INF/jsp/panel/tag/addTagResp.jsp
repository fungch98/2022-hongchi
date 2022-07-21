<%@page contentType="text/html" pageEncoding="UTF-8"%>
{"code":"${result.code}","msg":"${result.msg}",
    "src":"${upload.url}","uuid":"${upload.uuid}",
    "user":"${upload.userId.displayName}",
    "name":"${upload.uploadFileName}",
    "fileType":"${upload.fileType}",
    "modify":"${upload.getUploadDate()}"
    }
