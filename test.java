public void test() {
        //1.测试分为两块上传，将二进制数组拆分为两块
//        byte[] b = file.getBytes();
//        byte[] b1 = new byte[8];
//        byte[] b2 = new byte[b.length-8];
//        System.arraycopy(b,0,b1,0,8);
//        System.arraycopy(b,8,b2,0,b.length-8);
//        //先上传第一份，再追加第二个
//        b1 = deEncrypt.encryptFile(b1);
//        b2 = deEncrypt.encryptFile(b2);
//        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(b1);
//        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(b2);
//        StorePath storePath = appendFileStorageClient.uploadAppenderFile(groupName, inputStream1, b1.length, FilenameUtils.getExtension(file.getOriginalFilename()));
//        appendFileStorageClient.appendFile(groupName,storePath.getPath(),inputStream2,b2.length);
//        return storePath.getFullPath();
        //测试结束
    }