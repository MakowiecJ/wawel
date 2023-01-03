package com.mako.wawel.entity.movies;

import com.mako.wawel.entity.converter.SeatsConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

    @Convert(converter = SeatsConverter.class)
//    private List<Seat> seats = newSeats();
    private String[][] seats = newSeats();

    private LocalTime startTime;
    private LocalTime endTime;

    public static String[][] newSeats() {
//        List<Seat> seats = new ArrayList<>();
        String[][] seats = new String[12][18];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 18; j++) {
                if (j > 12 && j < 16 && i < 12 || j > 15 && i < 8) {
//                    Seat seat = Seat.builder()
//                            .row(i)
//                            .column(j)
//                            .availability(Availability.NIE_ISTNIEJE)
//                            .build();
//                    seats.add(seat);
                    seats[i][j] = Availability.NIE_ISTNIEJE.name();
                } else {
//                    Seat seat = Seat.builder()
//                            .row(i)
//                            .column(j)
//                            .availability(Availability.WOLNE)
//                            .build();
//                    seats.add(seat);
                    seats[i][j] = Availability.WOLNE.name();
                }
            }
        }
        return seats;
    }

//    public static String[][] seatsToArray(List<Seat> seats) {
//        String[][] array = new String[12][18];
//        for (Seat seat : seats) {
//            int rowIndex = seat.getRow();
//            int columnIndex = seat.getColumn();
//            String value = seat.getAvailability().toString();
//            array[rowIndex][columnIndex] = value;
//        }
//        return array;
//    }
}
