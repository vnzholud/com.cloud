package com.cloud.cloudserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//Выстраиваем обработчик событий
public class MainHandler extends ChannelInboundHandlerAdapter {

    //событие когда клиент подключился
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился: "+ ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg; //работа с байтбуффером
        // чтение из байтбуффера
        while (buf.readableBytes() > 0) {
            System.out.print((char) buf.readByte());
        }
        buf.release(); //очищаем буфер
    }

    //событие когда получили исключение
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
