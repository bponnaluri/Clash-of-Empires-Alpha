package engine.general.utility

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.Color
/**
 * This class represents lines. The coordinates are stored as shorts because they will be sent over a network.
 * @param xA
 * @param yA
 * @param xB
 * @param yB
 */
class Line(var xA:Short,var yA:Short,var xB:Short,var yB:Short) extends java.io.Serializable{

    def width()=Math.abs(xB-xA)
    def height()=Math.abs(yB-yA)

    def drawLine(bufferGraphics:ShapeRenderer){
    	bufferGraphics.begin(ShapeType.Line)
    	bufferGraphics.setColor(Color.BLACK)
        bufferGraphics.line(xA,yA,xB,yB)
    	bufferGraphics.end()
    }

    /**
     * This method scales the line by making it smaller,while maintaining the same midpoint.
     * @param scale
     */
    def shrink(scale:Double){
        
        val dX=(xB-xA).toDouble
        val dY=(yB-yA).toDouble
        val mul=(1-scale)

        xA=(xA+(dX*mul)).toShort
        xB=(xB-dX*mul).toShort
        yA=(yA+dY*mul).toShort
        yB=(yB-dY*mul).toShort

    }
    
    def drawArrow(bufferGraphics:ShapeRenderer){
        
    	bufferGraphics.begin(ShapeType.Line)
        val finalLoc: Location = new Location(xA,yA)
        val startLoc: Location = new Location(xB,yB)
        drawLine(bufferGraphics)

        //Get the midpoint of the line
        val midX: Int = (xA + xB) / 2
        val midY: Int = (yA + yB) / 2

        val distance: Double = finalLoc.distance(startLoc)
        val xPoints = new Array[Float](3)
        val yPoints = new Array[Float](3)
        val xW = (20 * (yB - yA) / distance).asInstanceOf[Float]
        val yW = (20 * (xB - xA) / distance).asInstanceOf[Float]

        xPoints(0) = midX + xW
        yPoints(0) = midY - yW
        xPoints(1) = midX - xW
        yPoints(1) = midY + yW
        xPoints(2) = midX + (30*(xB - xA)/distance).asInstanceOf[Float]
        yPoints(2) = midY + (30*(yB -yA)/distance).asInstanceOf[Float]
        bufferGraphics.polyline(xPoints++yPoints)
        bufferGraphics.end()
    }
    override def toString():String=xA+":"+yA+" "+xB+":"+yB
}