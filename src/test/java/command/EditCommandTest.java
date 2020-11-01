package command;

import eventlist.EventList;
import exception.CreatingFileException;
import exception.DataFileNotFoundException;
import exception.EditIndexOutOfBoundsException;
import exception.EmptyEventIndexException;
import exception.NoEditEventDescriptionException;
import exception.UndefinedEventException;
import exception.WrongEditFormatException;
import locationlist.BusStopList;
import locationlist.LocationList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import exception.NuScheduleException;
import parser.Parser;
import storage.Storage;
import ui.UI;


class EditCommandTest {

    @Test
    void execute_NoIndexSpecified_emptyEventIndexException() throws NuScheduleException {

        Assertions.assertThrows(EmptyEventIndexException.class, () -> {
            Command d = Parser.parse("edit", null, 0);
        });

    }

    @Test
    void execute_InputIsNotInteger_WrongEditFormatException() {
        Assertions.assertThrows(WrongEditFormatException.class, () -> {
            Command c = Parser.parse("edit c", null, 0);
        });
    }

    @Test
    void execute_IndexOutOfBounds_UndefinedEventException() throws CreatingFileException, DataFileNotFoundException {
        Storage storage = new Storage("data/events.txt","data/UserInfo.txt");
        LocationList locations = new LocationList();
        storage.loadLocationData(locations.getLocationList());
        Assertions.assertThrows(UndefinedEventException.class, () -> {
            Parser.parse("edit 1 assignment something /t 2020-02-02 20:00 /l somewhere", locations, 0)
                    .execute(new EventList(), new LocationList(), new BusStopList(), new UI(), storage);
        });
    }

    @Test
    void execute_NoEditDescriptionProvided_NoEditEventDescriptionException() {
        Assertions.assertThrows(NoEditEventDescriptionException.class, () -> {
            Command c = Parser.parse("edit 1", null,0);
        });
    }
}