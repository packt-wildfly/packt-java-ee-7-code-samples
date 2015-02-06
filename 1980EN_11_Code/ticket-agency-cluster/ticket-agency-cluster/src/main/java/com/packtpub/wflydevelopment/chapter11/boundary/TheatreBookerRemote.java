package com.packtpub.wflydevelopment.chapter11.boundary;

import com.packtpub.wflydevelopment.chapter11.exception.NoSuchSeatException;
import com.packtpub.wflydevelopment.chapter11.exception.NotEnoughMoneyException;
import com.packtpub.wflydevelopment.chapter11.exception.SeatBookedException;

import javax.ejb.Asynchronous;
import java.util.concurrent.Future;

public interface TheatreBookerRemote {

    int getAccountBalance();

    String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException;

    @Asynchronous
    Future<String> bookSeatAsync(int seatId);
}