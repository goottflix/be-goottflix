//package com.goottflix.config;
//
//import com.fazecast.jSerialComm.SerialPort;
//import com.goottflix.book.SerialPortReader;
//import com.goottflix.book.service.NfcService;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class SerialPortConfig {
//
//    private SerialPort serialPort;
//    private final NfcService nfcService;
//
//    @PostConstruct
//    public void initialize() {
//        serialPort = SerialPort.getCommPort("COM4"); // 포트 이름 설정 컴퓨터 장치에 따라 다를수있음 이거 windows기준이라
//        serialPort.setBaudRate(9600); // 전송속도
//        serialPort.setNumDataBits(8); // 8비트 데이터 전송
//        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT); //1비트의 정지비트 ( 송수신 동기화 용도 )
//        serialPort.setParity(SerialPort.NO_PARITY); //오류감지는 안하는걸로, 비트 사이에 패리티를 넣어서 오류를 검출하는것 (속도는 빨라짐)
//
//        if (serialPort.openPort()) {
//            System.out.println("Serial port opened successfully.");
//
//            // 데이터 리스너 등록
//            serialPort.addDataListener(new SerialPortReader(serialPort,nfcService));
//        } else {
//            System.out.println("Failed to open serial port.");
//        }
//    }
//
//    @PreDestroy
//    public void close() {
//        if (serialPort != null && serialPort.isOpen()) {
//            serialPort.closePort();
//        }
//    }
//}
