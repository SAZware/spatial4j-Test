package de.hasenburg.spatial4j.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.spatial4j.shape.Circle;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;

import static org.locationtech.spatial4j.distance.DistanceUtils.DEG_TO_KM;

public class Brain {

	public static Logger logger = LogManager.getLogger();


	public static void main (String[] args) {

		JtsSpatialContext ctx = JtsSpatialContext.GEO;
//		SpatialContext ctx = SpatialContext.GEO;

		// calculate distance between Berlin and Hamburg
		Point berlin = ctx.getShapeFactory().pointLatLon(52.5200, 13.4050);
		Point hamburg = ctx.getShapeFactory().pointLatLon(53.511, 9.9937);
		double distanceDegree = ctx.getDistCalc().distance(berlin, hamburg);
		double distanceKm = distanceDegree * DEG_TO_KM;
		logger.info("Distance between Berlin and Hamburg is {} degree or {}km", distanceDegree, distanceKm);

		// create circles around Berlin
		Circle smallC = ctx.getShapeFactory().circle(berlin, distanceDegree - 0.01);
		Circle mediumC = ctx.getShapeFactory().circle(berlin, distanceDegree);
		Circle largeC = ctx.getShapeFactory().circle(berlin, distanceDegree + 0.01);

		// test whether Hamburg is inside the circles around Berlin
		logger.info("Small circle {} Hamburg", smallC.relate(hamburg));
		logger.info("Medium circle {} Hamburg", mediumC.relate(hamburg));
		logger.info("Large circle {} Hamburg", largeC.relate(hamburg));

		// build a polygon rectangle around Berlin
		Shape rectP = ctx.getShapeFactory().polygon()
				.pointLatLon(53.0, 14.0)
				.pointLatLon(53.0, 13.0)
				.pointLatLon(52.0, 13.0)
				.pointLatLon(52.0, 14.0)
				.pointLatLon(53.0, 14.0)
				.build();

		Geometry geometryRectP = ctx.getShapeFactory().getGeometryFrom(rectP);
		logger.info("Polygon is a rectangle?: {}", geometryRectP.isRectangle());

		logger.info("Polygon {} Berlin", rectP.relate(berlin));
		logger.info("Polygon {} Hamburg", rectP.relate(hamburg));

		// build a polygon triangle around Berlin
		Shape triaP = ctx.getShapeFactory().polygon()
				.pointLatLon(54, 12.0)
				.pointLatLon(52, 15.0)
				.pointLatLon(50, 12.0)
				.pointLatLon(54, 12.0)
				.build();

		Geometry geometryTriaP = ctx.getShapeFactory().getGeometryFrom(triaP);
		logger.info("Polygon is a rectangle?: {}", geometryTriaP.isRectangle());

		logger.info("Polygon {} Berlin", triaP.relate(berlin));
		logger.info("Polygon {} Hamburg", triaP.relate(hamburg));
	}
}
