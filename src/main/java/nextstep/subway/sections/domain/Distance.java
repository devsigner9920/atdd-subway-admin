package nextstep.subway.sections.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Distance {
  private int distance;

  protected Distance() {}

  public Distance(int distance) {
    this.distance = distance;
  }

  public static Distance of(int distance) {
    return new Distance(distance);
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }
}
