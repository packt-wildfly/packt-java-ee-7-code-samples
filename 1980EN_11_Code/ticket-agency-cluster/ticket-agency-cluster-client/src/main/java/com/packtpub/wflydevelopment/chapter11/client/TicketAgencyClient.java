package com.packtpub.wflydevelopment.chapter11.client;

import com.packtpub.wflydevelopment.chapter11.boundary.TheatreBookerRemote;
import com.packtpub.wflydevelopment.chapter11.boundary.TheatreInfoRemote;
import com.packtpub.wflydevelopment.chapter11.exception.NoSuchSeatException;
import com.packtpub.wflydevelopment.chapter11.exception.NotEnoughMoneyException;
import com.packtpub.wflydevelopment.chapter11.exception.SeatBookedException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TicketAgencyClient {

    private static final Logger logger = Logger.getLogger(TicketAgencyClient.class.getName());

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.jboss").setLevel(Level.SEVERE);
        Logger.getLogger("org.xnio").setLevel(Level.SEVERE);

        new TicketAgencyClient().run();
    }

    private final Context context;
    private final List<Future<String>> lastBookings = new ArrayList<>();
    private TheatreInfoRemote theatreInfo;
    private TheatreBookerRemote theatreBooker;

    public TicketAgencyClient() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        this.context = new InitialContext(jndiProperties);
    }

    private enum Command {
        BOOK, BOOKASYNC, MAIL, LIST, MONEY, QUIT, INVALID;

        public static Command parseCommand(String stringCommand) {
            try {
                return valueOf(stringCommand.trim().toUpperCase());
            } catch (IllegalArgumentException iae) {
                return INVALID;
            }
        }
    }

    private void run() throws NamingException {
        this.theatreInfo = lookupTheatreInfoEJB();
        this.theatreBooker = lookupTheatreBookerEJB();

        showWelcomeMessage();

        while (true) {
            final String stringCommand = IOUtils.readLine("> ");
            final Command command = Command.parseCommand(stringCommand);
            switch (command) {
                case BOOK:
                    handleBook();
                    break;
                case BOOKASYNC:
                    handleBookAsync();
                    break;
                case MAIL:
                    handleMail();
                    break;
                case LIST:
                    handleList();
                    break;
                case MONEY:
                    handleMoney();
                    break;
                case QUIT:
                    handleQuit();
                    break;

                default:
                    logger.warning("Unknown command " + stringCommand);
            }
        }
    }

    private void handleBook() {
        int seatId;

        try {
            seatId = IOUtils.readInt("Enter SeatId: ");
        } catch (NumberFormatException e1) {
            logger.warning("Wrong SeatId format!");
            return;
        }

        try {
            final String retVal = theatreBooker.bookSeat(seatId);
            System.out.println(retVal);
        } catch (SeatBookedException | NotEnoughMoneyException | NoSuchSeatException e) {
            logger.warning(e.getMessage());
            return;
        }
    }

    private void handleBookAsync() {
        String text = IOUtils.readLine("Enter SeatId: ");
        int seatId;

        try {
            seatId = Integer.parseInt(text);
        } catch (NumberFormatException e1) {
            logger.warning("Wrong seatId format!");
            return;
        }

        lastBookings.add(theatreBooker.bookSeatAsync(seatId));
        logger.info("Booking issued. Verify your mail!");
    }

    private void handleMail() {
        boolean displayed = false;
        final List<Future<String>> notFinished = new ArrayList<>();
        for (Future<String> booking : lastBookings) {
            if (booking.isDone()) {
                try {
                    final String result = booking.get();
                    logger.info("Mail received: " + result);
                    displayed = true;
                } catch (InterruptedException | ExecutionException e) {
                    logger.warning(e.getMessage());
                }
            } else {
                notFinished.add(booking);
            }
        }

        lastBookings.retainAll(notFinished);
        if (!displayed) {
            logger.info("No mail received!");
        }
    }

    private void handleList() {
        logger.info(theatreInfo.printSeatList());
    }

    private void handleMoney() {
        final int accountBalance = theatreBooker.getAccountBalance();
        logger.info("You have: " + accountBalance + " money left.");
    }

    private void handleQuit() {
        logger.info("Bye");
        System.exit(0);
    }

    private TheatreInfoRemote lookupTheatreInfoEJB() throws NamingException {
        return (TheatreInfoRemote) context.lookup("ejb:/ticket-agency-cluster//TheatreInfo!com.packtpub.wflydevelopment.chapter11.boundary.TheatreInfoRemote");
    }

    private TheatreBookerRemote lookupTheatreBookerEJB() throws NamingException {
        return (TheatreBookerRemote) context.lookup("ejb:/ticket-agency-cluster//TheatreBooker!com.packtpub.wflydevelopment.chapter11.boundary.TheatreBookerRemote?stateful");
    }

    private void showWelcomeMessage() {
        System.out.println("Theatre booking system");
        System.out.println("=====================================");
        System.out.println("Commands: book, bookasync, list, mail, money, quit");
    }
}
