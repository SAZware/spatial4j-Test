How to calculate distances?

```Java
Point berlin = ctx.getShapeFactory().pointLatLon(52.5200, 13.4050);
Point hamburg = ctx.getShapeFactory().pointLatLon(53.511, 9.9937);
double distanceDegree = ctx.getDistCalc().distance(berlin, hamburg);
double distanceKm = distanceDegree * DEG_TO_KM;
```

For more information, see Brain.java ;).