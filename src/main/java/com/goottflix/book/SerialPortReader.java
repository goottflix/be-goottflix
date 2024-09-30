package com.goottflix.book;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.goottflix.book.controller.BookController;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class SerialPortReader implements SerialPortDataListener {
    private final SerialPort serialPort;
    private final BookController bookController;
    private StringBuilder buffer = new StringBuilder();


    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            try {
                InputStream inputStream = serialPort.getInputStream();
                byte[] readBuffer = new byte[1024];
                int numBytes = inputStream.read(readBuffer);
                if (numBytes > 0) {
                    // 데이터를 문자열로 변환 후 버퍼에 추가
                    String rawData = new String(readBuffer, 0, numBytes, StandardCharsets.UTF_8);
                    buffer.append(rawData);  // 데이터를 누적하여 버퍼에 저장

                    // 특정 키워드가 버퍼에 포함되면 처리
                    if (buffer.indexOf("UID Value") != -1 || buffer.indexOf("UID Value :") != -1) {
                        String completeMessage = buffer.toString().trim();  // 전체 데이터를 추출
                        buffer.setLength(0);  // 버퍼 초기화

                        // 처리된 메시지 출력
                        System.out.println("Processed Data: " + completeMessage);

                        // 데이터를 BookController로 전달
                        bookController.sendNfcData(completeMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

}

