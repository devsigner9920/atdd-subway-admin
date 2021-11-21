package nextstep.subway.line.domain;

import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LineTest {

    private static final Station 강남역 = new Station("강남역");
    private static final Station 교대역 = new Station("교대역");
    private static final Station 역삼역 = new Station("역삼역");
    private static final Station 삼성역 = new Station("삼성역");
    private static final Station 잠실역 = new Station("잠실역");

    @Test
    void getStations_역들을_조회한다() {
        // given
        Line 이호선 = new Line("2호선", "green");
        List<Section> sections = Arrays.asList(
                new Section(이호선, 삼성역, 잠실역, 20),
                new Section(이호선, 교대역, 강남역, 10),
                new Section(이호선, 역삼역, 삼성역, 30),
                new Section(이호선, 강남역, 역삼역, 40));
        이호선.updateSections(sections);

        // when
        List<Station> stations = 이호선.getStations();

        // then
        assertThat(stations).containsExactly(교대역, 강남역, 역삼역, 삼성역, 잠실역);
    }

    @Test
    void hasStation_역이_존재하는지_확인한다() {
        Line 이호선 = new Line("2호선", "green", 강남역, 삼성역, 10);
        assertThat(이호선.hasStation(강남역)).isTrue();
    }

    @Test
    void updateUpStation_상행을_업데이트한다() {
        Line 이호선 = new Line("2호선", "green", 강남역, 삼성역, 10);
        이호선.updateUpStation(강남역, 역삼역, 3);
        assertThat(이호선.getSections()).contains(new Section(이호선, 역삼역, 삼성역, 7));
    }

    @Test
    void addSection_구간을_추가한다() {
        Line 이호선 = new Line("2호선", "green", 강남역, 삼성역, 10);
        이호선.addSection(강남역, 역삼역, 3);
        assertThat(이호선.getSections()).contains(new Section(이호선, 강남역, 역삼역, 3));
    }

    @Test
    void updateDownStation_하행역을_업데이트한다() {
        Line 이호선 = new Line("2호선", "green", 강남역, 삼성역, 10);
        이호선.updateDownStation(역삼역, 삼성역, 3);
        assertThat(이호선.getSections()).contains(new Section(이호선, 강남역, 역삼역, 7));
    }

    @Test
    void removeSection_상행역을_삭제한다() {
        // given
        Line 이호선 = new Line("2호선", "green", 역삼역, 삼성역, 10);
        이호선.addSection(강남역, 역삼역, 3);
        assertThat(이호선.getStations()).containsExactly(강남역, 역삼역, 삼성역);

        // when
        이호선.removeSection(강남역);

        // then
        assertAll(
                () -> assertThat(이호선.getSections().size()).isEqualTo(1),
                () -> assertThat(이호선.getSections()).contains(new Section(이호선, 역삼역, 삼성역, 10)),
                () -> assertThat(이호선.getStations()).containsExactly(역삼역, 삼성역)
        );
    }

    @Test
    void removeSection_하행역을_삭제한다() {
        // given
        Line 이호선 = new Line("2호선", "green", 역삼역, 삼성역, 10);
        이호선.addSection(강남역, 역삼역, 3);
        assertThat(이호선.getStations()).containsExactly(강남역, 역삼역, 삼성역);

        // when
        이호선.removeSection(삼성역);

        // then
        assertAll(
                () -> assertThat(이호선.getSections().size()).isEqualTo(1),
                () -> assertThat(이호선.getSections()).contains(new Section(이호선, 강남역, 역삼역, 3)),
                () -> assertThat(이호선.getStations()).containsExactly(강남역, 역삼역)
        );
    }

    @Test
    void removeSection_중간역을_삭제한다() {
        // given
        Line 이호선 = new Line("2호선", "green", 역삼역, 삼성역, 10);
        이호선.addSection(강남역, 역삼역, 3);
        assertThat(이호선.getStations()).containsExactly(강남역, 역삼역, 삼성역);

        // when
        이호선.removeSection(역삼역);

        // then
        assertAll(
                () -> assertThat(이호선.getSections().size()).isEqualTo(1),
                () -> assertThat(이호선.getSections()).contains(new Section(이호선, 강남역, 삼성역, 13)),
                () -> assertThat(이호선.getStations()).containsExactly(강남역, 삼성역)
        );
    }
}