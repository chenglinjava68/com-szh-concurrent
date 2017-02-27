package com.szh.demo.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class QrCode {

    public static void main(String[] args) throws WriterException, IOException,
            NotFoundException {
        String qrCodeData = "https://www.baidu.com";
        String filePath = "1111.jpg";
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.MARGIN, 1);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //  createQRCode(qrCodeData, filePath, charset, hintMap, 300, 300);
        System.out.println("QR Code image created successfully!");
        //   System.out.println("Data read from QR Code: " + readQRCode(filePath, charset, hintMap));
        InputStream inputStream = createQRCodeToStream(qrCodeData, filePath, charset, hintMap, 300, 300);
        OutputStream os = new FileOutputStream(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }


       // System.out.println(QrCode.readQRCode("/home/zhihaosong/workspace/com-szh-concurrent/1477134992.png", "UTF-8", null));
    }

    public static void createQRCode(String qrCodeData, String filePath,
                                    String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                .lastIndexOf('.') + 1), new File(filePath));
    }

    /*创建二维码以流的方式*/


    public static InputStream createQRCodeToStream(String qrCodeData, String filePath,
                                                   String charset, Map hintMap, int qrCodeheight, int qrCodewidth) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes("UTF-8"), "ISO-8859-1"),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            MatrixToImageWriter.writeToStream(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), byteArrayOutputStream);
            byteArrayOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());


    }

      /*创建二维码以流的方式*/


    public static void getQrCode() {
        String qrCodeData = "https://www.baidu.com";
        String filePath = "1111.jpg";
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.MARGIN, 1);

        //  createQRCode(qrCodeData, filePath, charset, hintMap, 300, 300);
        System.out.println("QR Code image created successfully!");

        //   System.out.println("Data read from QR Code: "
        //    + readQRCode(filePath, charset, hintMap));

        InputStream inputStream = createQRCodeToStream(qrCodeData, filePath, charset, hintMap, 300, 300);
        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        /*读取二维码*/



    public static String readQRCode(String filePath, String charset, Map hintMap)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                hintMap);
        return qrCodeResult.getText();
    }
}
