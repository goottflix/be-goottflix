//package com.goottflix.config;
//
//import com.fazecast.jSerialComm.SerialPort;
//import com.goottflix.book.SerialPortReader;
//import com.goottflix.book.controller.BookController;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SerialPortConfig {
//    private SerialPort serialPort;
//    private final BookController bookController;
//
//    // BookController를 의존성 주입
//    public SerialPortConfig(BookController bookController) {
//        this.bookController = bookController;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        serialPort = SerialPort.getCommPort("COM4"); // 포트 이름은 실제 포트에 맞게 변경
//        serialPort.setBaudRate(9600);
//        serialPort.setNumDataBits(8);
//        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
//        serialPort.setParity(SerialPort.NO_PARITY);
//
//        if (serialPort.openPort()) {
//            System.out.println("Serial port opened successfully.");
//
//            // 데이터 리스너 등록
//            serialPort.addDataListener(new SerialPortReader(serialPort, bookController));
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
