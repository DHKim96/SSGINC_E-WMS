package com.ssginc.ewms.util;

import com.ssginc.ewms.dashboard.dto.GridDto;

/**
 * 위도, 경도 값을 격자 상의 X,Y 좌표로 변환해주는 클래스
 */
public class GpsTransfer {

    private static final double RE = 6371.00877; // 지구 반경(km)
    private static final double GRID = 5.0; // 격자 간격(km)
    private static final double SLAT1 = 30.0; // 투영 위도1(degree)
    private static final double SLAT2 = 60.0; // 투영 위도2(degree)
    private static final double OLON = 126.0; // 기준점 경도(degree)
    private static final double OLAT = 38.0; // 기준점 위도(degree)
    private static final double XO = 43.0; // 기준점 X 좌표(GRID)
    private static final double YO = 136.0; // 기준점 Y 좌표(GRID)
    private static final double DEGRAD = Math.PI / 180.0;

    private final double sn;
    private final double sf;
    private double ro;

    public GpsTransfer() {
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olat = OLAT * DEGRAD;

        this.sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) /
                Math.log(Math.tan(Math.PI * 0.25 + slat2 * 0.5) /
                        Math.tan(Math.PI * 0.25 + slat1 * 0.5));
        this.sf = Math.pow(Math.tan(Math.PI * 0.25 + slat1 * 0.5), sn) *
                Math.cos(slat1) / sn;
        this.ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        this.ro = (RE / GRID) * sf / Math.pow(ro, sn);
    }

    public GridDto transfer(double latitude, double longitude) {

        double ra = Math.tan(Math.PI * 0.25 + (latitude * DEGRAD) * 0.5);
        ra = (RE / GRID) * sf / Math.pow(ra, sn);

        double theta = longitude * DEGRAD - OLON * DEGRAD;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        GridDto gridDto = new GridDto();
        gridDto.setX((int) (Math.floor(ra * Math.sin(theta) + XO + 0.5)));
        gridDto.setY((int) (Math.floor(ro - ra * Math.cos(theta) + YO + 0.5)));
        return gridDto;
    }
}
