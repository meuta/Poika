package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class TriangleForwardShape(private val roundRadius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            val CR = size.width
            val AR = size.height / 2

            val DAE = atan(CR / AR)
            val DAQ = PI.toFloat() / 2 - DAE
            val DAM = DAE / 2
            val AD = roundRadius / tan(DAM)
            val DQ = AD * sin(DAQ)
            val AQ = AD * cos(DAQ)

            //move to point D
            moveTo(AQ, DQ)

            val GCN = (PI.toFloat() - 2 * DAE) / 2
            val CG = roundRadius / tan(GCN)
            val GT = CG * sin(GCN)
            val CT = CG * cos(GCN)

            // line to point G
            lineTo(CR - CT, AR - GT)

            val CN = roundRadius / sin(GCN)
            val CNG = PI.toFloat() / 2 - GCN
            // right arc
            arcToRad(Rect(Offset(CR - CN, AR), roundRadius), -CNG, 2 * CNG, false)

            val AB = size.height

            // line to point K
            lineTo(AQ, AB - DQ)

            val OBV = DAM + DAQ
            val BO = roundRadius / sin(DAM)
            val BV = BO * cos(OBV)
            val OV = BO * sin(OBV)

            val BOV = PI.toFloat() - OBV
            val BOK = PI.toFloat() - DAM
            val KOV = BOK - BOV
            val KOU = PI.toFloat() / 2 - KOV
            val JOK = PI.toFloat() - DAE

            // bottom left arc
            arcToRad(Rect(Offset(BV, AB - OV), roundRadius), KOU, JOK, false)

            // line to point E
            lineTo(0f, AD)

            // top left arc
            arcToRad(Rect(Offset(BV, OV), roundRadius), PI.toFloat(), JOK, false)
        }
    )
}


class TriangleBackwardShape(private val roundRadius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            val AR = size.width
            val BR = size.height / 2

            val MAD = atan(BR / AR)

            val AD = roundRadius / tan(MAD)
            val DQ = AD * sin(MAD)
            val AQ = AD * cos(MAD)

            // move to point D
            moveTo(AQ, size.height / 2 - DQ)


            val GBH = (PI.toFloat() - 2 * MAD) / 2
            val GBN = GBH / 2
            val GBT = PI.toFloat() / 2 - GBH
            val BG = roundRadius / tan(GBN)
            val GT = BG * sin(GBT)
            val BT = BG * cos(GBT)

            // line to point G
            lineTo(AR - BT, GT)

            val BN = roundRadius / sin(GBN)
            val NH = BN * sin(GBN)
            val BH = BN * cos(GBN)
            val GNH = PI.toFloat() - GBH

            // top right arc
            arcToRad(Rect(Offset(AR - NH, BH), roundRadius), -GNH, GNH, false)

            // line to point K
            lineTo(size.width, size.height - BH)

            // bottom right arc
            arcToRad(Rect(Offset(size.width - NH, size.height - BH), roundRadius), 0f, GNH, false)

            // line to point E
            lineTo(AQ, BR + DQ)

            val AM = roundRadius / sin(MAD)
            val FME = PI.toFloat() - MAD

            // left arc
            arcToRad(Rect(Offset(AM, BR), roundRadius), FME, 2 * MAD, false)
        }
    )
}