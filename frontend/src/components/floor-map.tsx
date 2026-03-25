"use client";

import { useRef, useState, useEffect, useMemo } from "react";
import type { Seat, CurrentSeat } from "@/types/api";
import {
  calculateFloorMapLayout,
  SEAT_SIZE,
} from "@/lib/floor-map-utils";
import styles from "./floor-map.module.css";

interface FloorMapProps {
  location: string;
  seats: Seat[];
  occupiedSeats: CurrentSeat[];
}

const formatOccupantName = (name: string, maxLength = 6): string => {
  return name.length > maxLength ? `${name.slice(0, maxLength)}…` : name;
};

export default function FloorMap({ location, seats, occupiedSeats }: FloorMapProps) {
  const svgRef = useRef<SVGSVGElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);

  const [scale, setScale] = useState(1);
  const [panX, setPanX] = useState(0);
  const [panY, setPanY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const [dragStart, setDragStart] = useState({ x: 0, y: 0 });

  const layout = useMemo(() => calculateFloorMapLayout(seats), [seats]);
  const { seatCoordinates, tableDecorations, width: canvasWidth, height: canvasHeight } = layout;

  // 占有座席マップ
  const occupiedMap = useMemo(
    () => new Map<number, CurrentSeat>(occupiedSeats.map((cs) => [cs.seat.id, cs])),
    [occupiedSeats]
  );

  // マウスホイールでズーム
  useEffect(() => {
    const handleWheel = (e: WheelEvent) => {
      if (svgRef.current && containerRef.current?.contains(e.target as Node)) {
        e.preventDefault();
        const delta = e.deltaY > 0 ? 0.9 : 1.1;
        setScale((prev) => {
          const newScale = prev * delta;
          return Math.max(0.5, Math.min(3, newScale));
        });
      }
    };

    window.addEventListener("wheel", handleWheel, { passive: false });
    return () => window.removeEventListener("wheel", handleWheel);
  }, []);

  // ドラッグでパンニング
  const handleMouseDown = (e: React.MouseEvent<SVGSVGElement>) => {
    if (e.button !== 0) return; // 左クリックのみ
    setIsDragging(true);
    setDragStart({ x: e.clientX - panX, y: e.clientY - panY });
  };

  const handleMouseMove = (e: React.MouseEvent<SVGSVGElement>) => {
    if (!isDragging) return;
    setPanX(e.clientX - dragStart.x);
    setPanY(e.clientY - dragStart.y);
  };

  const handleMouseUp = () => {
    setIsDragging(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h3>{location}</h3>
        <div className={styles.controls}>
          <button
            className={styles.controlBtn}
            onClick={() => setScale((prev) => Math.max(0.5, prev - 0.1))}
          >
            −
          </button>
          <span className={styles.zoomLevel}>{Math.round(scale * 100)}%</span>
          <button
            className={styles.controlBtn}
            onClick={() => setScale((prev) => Math.min(3, prev + 0.1))}
          >
            +
          </button>
          <button
            className={styles.controlBtn}
            onClick={() => {
              setScale(1);
              setPanX(0);
              setPanY(0);
            }}
          >
            リセット
          </button>
        </div>
      </div>

      <div ref={containerRef} className={styles.viewport}>
        <svg
          ref={svgRef}
          className={styles.canvas}
          width={canvasWidth}
          height={canvasHeight}
          viewBox={`0 0 ${canvasWidth} ${canvasHeight}`}
          style={{
            transform: `translate(${panX}px, ${panY}px) scale(${scale})`,
            transformOrigin: "0 0",
            cursor: isDragging ? "grabbing" : "grab",
          }}
          onMouseDown={handleMouseDown}
          onMouseMove={handleMouseMove}
          onMouseUp={handleMouseUp}
          onMouseLeave={handleMouseUp}
        >
          {/* グリッド線（オプション）*/}
          <defs>
            <pattern id="grid" width="70" height="70" patternUnits="userSpaceOnUse">
              <path d="M 70 0 L 0 0 0 70" fill="none" stroke="#e5e7eb" strokeWidth="0.5" />
            </pattern>
          </defs>
          <rect width={canvasWidth} height={canvasHeight} fill="url(#grid)" />

          {tableDecorations.map((table) => {
            if (table.kind === "circle") {
              return (
                <circle
                  key={table.id}
                  cx={table.cx}
                  cy={table.cy}
                  r={table.r}
                  className={styles.tableCircle}
                />
              );
            }

            return (
              <rect
                key={table.id}
                x={table.x}
                y={table.y}
                width={table.width}
                height={table.height}
                rx={table.rx}
                className={styles.tableRect}
              />
            );
          })}

          {/* 座席 */}
          {seatCoordinates.map((coord) => {
            const occupied = occupiedMap.get(coord.seatId);
            const seatRadius = coord.section === "A" ? SEAT_SIZE.A_SEAT_RADIUS : SEAT_SIZE.GRID_CIRCLE_SEAT_RADIUS;
            const centerX = coord.shape === "rect" ? coord.x + SEAT_SIZE.WIDTH / 2 : coord.x + seatRadius;
            const sectionLabelY = coord.shape === "rect" ? coord.y + 22 : coord.y + seatRadius - 6;
            const secondaryLabelY = coord.shape === "rect" ? coord.y + 36 : coord.y + seatRadius + 8;
            const secondaryText = occupied ? formatOccupantName(occupied.userName) : String(coord.number);
            return (
              <g key={coord.seatId}>
                {occupied && <title>{occupied.userName}</title>}
                {coord.shape === "rect" ? (
                  <rect
                    x={coord.x}
                    y={coord.y}
                    width={SEAT_SIZE.WIDTH}
                    height={SEAT_SIZE.HEIGHT}
                    rx="4"
                    className={occupied ? styles.seatOccupied : styles.seatAvailable}
                  />
                ) : (
                  <circle
                    cx={coord.x + seatRadius}
                    cy={coord.y + seatRadius}
                    r={seatRadius}
                    className={occupied ? styles.seatOccupied : styles.seatAvailable}
                  />
                )}
                <text
                  x={centerX}
                  y={sectionLabelY}
                  className={styles.seatLabel}
                  textAnchor="middle"
                  dominantBaseline="middle"
                >
                  {coord.section}
                </text>
                <text
                  x={centerX}
                  y={secondaryLabelY}
                  className={occupied ? styles.occupiedUserName : styles.seatNumber}
                  textAnchor="middle"
                  dominantBaseline="middle"
                >
                  {secondaryText}
                </text>
              </g>
            );
          })}
        </svg>
      </div>

      <div className={styles.legend}>
        <span className={styles.legendItem}>
          <span className={styles.legendBoxAvailable}></span>
          空席
        </span>
        <span className={styles.legendItem}>
          <span className={styles.legendBoxOccupied}></span>
          使用中
        </span>
      </div>
    </div>
  );
}
