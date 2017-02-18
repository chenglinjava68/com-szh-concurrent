/*
package com.szh.demo.qrcode;

import com.dajie.common.file.enums.FileSavedType;
import com.dajie.common.file.model.UploadReturnModel;
import com.dajie.common.file.service.FileUploadService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by zhihaosong on 16-5-18.
 *//*

public class QRCodeUtil {

    public static InputStream createQRCode(String qrCodeData, int qrCodeheight, int qrCodewidth)
            throws WriterException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.MARGIN, 1);
        String filePath = "qr.jpg";
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes("UTF-8"), "ISO-8859-1"),
                BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
        MatrixToImageWriter.writeToStream(matrix, "jpeg", outputStream);
        outputStream.flush();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public static void main(String[] args) throws IOException, WriterException {
        InputStream inputStream = createQRCode("http://www.dajie.com?key=ksinak", 300, 300);
       // UploadReturnModel model = FileUploadService.uploadFromStream(inputStream, "qr.jpg", FileSavedType.common_image);
    }
}
*/
