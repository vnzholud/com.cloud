package com.cloud.cloudclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Network {


    private SocketChannel channel;

    private static final String HOST = "localhost";
    private static final int PORT = 8189;


    public Network() {
        Thread t = new Thread(() -> {

            NioEventLoopGroup workerGroup = new NioEventLoopGroup();// создаем пул потоков (1 поток)

            try {

                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {

                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder());
                            }
                        });


                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();  //ждем события закрытия

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }


        });
        t.start();
    }


        public void close() {
            channel.close();
        }

        public void sendMessage(String str) {
            channel.writeAndFlush(str);
        }

}

