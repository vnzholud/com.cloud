package com.cloud.cloudserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerApp {

    public static void main(String[] args) {

        //создаем програмный сервер сервер для 2 пулов потоков
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //подключающиеся клиенты (до 20-30)
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // для обработки потоков

            try {
                ServerBootstrap b = new ServerBootstrap(); //преднастройка сервера
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class) //выделяем канал стандартный на NIO
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new MainHandler());// обрабатываем вх данные
                            }
                        });
                ChannelFuture future = b.bind(8089).sync(); // запускаем сервер
                future.channel().closeFuture().sync(); //ждем остановки канала

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                bossGroup.shutdownGracefully(); // закрываем каналы после остановки сервера
                workerGroup.shutdownGracefully();
            }
    }

}
