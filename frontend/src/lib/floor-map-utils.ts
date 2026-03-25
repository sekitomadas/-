import type { Seat } from "@/types/api";

/**
 * フロアマップ用の座席座標計算ユーティリティ
 */

export interface SeatCoordinates {
  seatId: number;
  seatName: string;
  section: string;
  number: number;
  x: number;
  y: number;
  shape: "rect" | "circle";
}

export interface TableDecoration {
  id: string;
  kind: "circle" | "rect";
  cx?: number;
  cy?: number;
  r?: number;
  x?: number;
  y?: number;
  width?: number;
  height?: number;
  rx?: number;
}

export interface FloorMapLayout {
  seatCoordinates: SeatCoordinates[];
  tableDecorations: TableDecoration[];
  width: number;
  height: number;
}

const SEAT_WIDTH = 72;
const SEAT_HEIGHT = 50;
const SEAT_GAP = 10;
const DEFAULT_ROW_PITCH = SEAT_HEIGHT + SEAT_GAP;
const B_ROW_PITCH = 50;
const SECTION_GAP_X = 80;

const SECTION_ORIGIN: Record<string, { x: number; y: number }> = {
  S: { x: 20, y: 20 },
  A: { x: 360, y: 20 },
  B: { x: 820, y: 20 },
};

const S_COLS = 4;
const B_COLS = 1;
const A_TABLE_COLS = 3;
const A_TABLE_ROWS = 5;
const A_SEATS_PER_TABLE = 4;
const A_TABLE_RADIUS = 30;
const A_SEAT_RADIUS = 20;
const A_SEAT_DIAGONAL_OFFSET = 31;
const A_TABLE_GAP_X = 160;
const A_TABLE_GAP_Y = 150;
const GRID_CIRCLE_SEAT_RADIUS = 20;

const S_MAX_SEATS = 20; // 4列 x 5行
const A_MAX_SEATS = 60; // 4人テーブル x 15卓
const B_MAX_SEATS = 15; // 1列 x 20行

const S_SEAT_ROW_SLOTS = [1, 2.2, 5, 6.2, 9];
const S_TABLE_ROW_SLOTS = [0, 3, 4, 7, 8];

/**
 * 座席名から section と number を抽出
 * 例: "S-116" → { section: "S", number: 116 }
 */
export const parseSeatName = (
  seatName: string
): { section: string; number: number } | null => {
  const match = seatName.match(/^([A-Z])-(\d+)$/);
  if (!match) return null;
  return {
    section: match[1],
    number: parseInt(match[2], 10),
  };
};

const gridSeatCoordinates = (
  seatId: number,
  seatName: string,
  section: string,
  number: number,
  index: number,
  cols: number,
  origin: { x: number; y: number },
  shape: "rect" | "circle" = "rect",
  rowSlotResolver?: (rowIndex: number) => number,
  rowPitch = DEFAULT_ROW_PITCH
): SeatCoordinates => {
  const col = (index % cols) + 1;
  const row = Math.floor(index / cols);
  const rowSlot = rowSlotResolver ? rowSlotResolver(row) : row;
  const baseX = origin.x + (col - 1) * (SEAT_WIDTH + SEAT_GAP);
  const baseY = origin.y + rowSlot * rowPitch;
  const x = shape === "circle" ? baseX + Math.floor((SEAT_WIDTH - GRID_CIRCLE_SEAT_RADIUS * 2) / 2) : baseX;
  const y = shape === "circle" ? baseY + Math.floor((SEAT_HEIGHT - GRID_CIRCLE_SEAT_RADIUS * 2) / 2) : baseY;
  return {
    seatId,
    seatName,
    section,
    number,
    x,
    y,
    shape,
  };
};

const roundTableSeatCoordinates = (
  seatId: number,
  seatName: string,
  number: number,
  index: number,
  origin: { x: number; y: number }
): SeatCoordinates => {
  const tableIndex = Math.floor(index / A_SEATS_PER_TABLE);
  const seatInTable = index % A_SEATS_PER_TABLE;

  const tableCol = tableIndex % A_TABLE_COLS;
  const tableRow = Math.floor(tableIndex / A_TABLE_COLS);

  const cx = origin.x + tableCol * A_TABLE_GAP_X + 50;
  const cy = origin.y + tableRow * A_TABLE_GAP_Y + 50;

  const offsets = [
    { x: -A_SEAT_DIAGONAL_OFFSET, y: -A_SEAT_DIAGONAL_OFFSET },
    { x: A_SEAT_DIAGONAL_OFFSET, y: -A_SEAT_DIAGONAL_OFFSET },
    { x: -A_SEAT_DIAGONAL_OFFSET, y: A_SEAT_DIAGONAL_OFFSET },
    { x: A_SEAT_DIAGONAL_OFFSET, y: A_SEAT_DIAGONAL_OFFSET },
  ];

  const offset = offsets[seatInTable];
  return {
    seatId,
    seatName,
    section: "A",
    number,
    x: cx + offset.x - A_SEAT_RADIUS,
    y: cy + offset.y - A_SEAT_RADIUS,
    shape: "circle",
  };
};

const aTableDecorations = (origin: { x: number; y: number }): TableDecoration[] => {
  const tables: TableDecoration[] = [];
  for (let row = 0; row < A_TABLE_ROWS; row += 1) {
    for (let col = 0; col < A_TABLE_COLS; col += 1) {
      const cx = origin.x + col * A_TABLE_GAP_X + 50;
      const cy = origin.y + row * A_TABLE_GAP_Y + 50;
      tables.push({
        id: `A-table-${row}-${col}`,
        kind: "circle",
        cx,
        cy,
        r: A_TABLE_RADIUS,
      });
    }
  }
  return tables;
};

const sTableDecorations = (origin: { x: number; y: number }): TableDecoration[] => {
  const tables: TableDecoration[] = [];
  const tableWidth = S_COLS * (SEAT_WIDTH + SEAT_GAP) - SEAT_GAP;
  const tableHeight = 32;
  const singleBottomMargin = 4;
  const blockTopMargin = 6;

  const singleRows = S_TABLE_ROW_SLOTS.filter((slot) => !S_TABLE_ROW_SLOTS.includes(slot - 1) && !S_TABLE_ROW_SLOTS.includes(slot + 1));
  const doubleStartRows = S_TABLE_ROW_SLOTS.filter((slot) => S_TABLE_ROW_SLOTS.includes(slot + 1) && !S_TABLE_ROW_SLOTS.includes(slot - 1));

  for (const slot of singleRows) {
    const y = origin.y + (slot + 1) * DEFAULT_ROW_PITCH - tableHeight - singleBottomMargin;
    const x = origin.x;
    tables.push({
      id: `S-table-${slot}`,
      kind: "rect",
      x,
      y,
      width: tableWidth,
      height: tableHeight,
      rx: 4,
    });
  }

  for (const startSlot of doubleStartRows) {
    const y = origin.y + startSlot * DEFAULT_ROW_PITCH + blockTopMargin;
    const x = origin.x;
    const height = DEFAULT_ROW_PITCH * 2 - blockTopMargin * 2;
    tables.push({
      id: `S-table-${startSlot}-${startSlot + 1}`,
      kind: "rect",
      x,
      y,
      width: tableWidth,
      height,
      rx: 4,
    });
  }
  return tables;
};

const bConnectedTableDecoration = (origin: { x: number; y: number }): TableDecoration => {
  const tableX = origin.x + SEAT_WIDTH + 22;
  const tableHeight = B_MAX_SEATS * B_ROW_PITCH - (B_ROW_PITCH - SEAT_HEIGHT);
  return {
    id: "B-table-connected",
    kind: "rect",
    x: tableX,
    y: origin.y,
    width: 36,
    height: tableHeight,
    rx: 5,
  };
};

export const calculateFloorMapLayout = (seats: Seat[]): FloorMapLayout => {
  const seatCoordinates: SeatCoordinates[] = [];
  const tableDecorations: TableDecoration[] = [];

  const parsedSeats = seats
    .map((seat) => {
      const parsed = parseSeatName(seat.name);
      if (!parsed) return null;
      return { seat, section: parsed.section, number: parsed.number };
    })
    .filter((item): item is { seat: Seat; section: string; number: number } => item !== null)
    .sort((a, b) => a.section.localeCompare(b.section) || a.number - b.number);

  const bySection: Record<string, Array<{ seat: Seat; number: number }>> = {};
  for (const item of parsedSeats) {
    if (!bySection[item.section]) bySection[item.section] = [];
    bySection[item.section].push({ seat: item.seat, number: item.number });
  }

  const sSeats = (bySection.S ?? []).slice(0, S_MAX_SEATS);
  for (let i = 0; i < sSeats.length; i += 1) {
    const item = sSeats[i];
    seatCoordinates.push(
      gridSeatCoordinates(
        item.seat.id,
        item.seat.name,
        "S",
        item.number,
        i,
        S_COLS,
        SECTION_ORIGIN.S,
        "circle",
        (rowIndex) => S_SEAT_ROW_SLOTS[Math.min(rowIndex, S_SEAT_ROW_SLOTS.length - 1)]
      )
    );
  }

  const aSeats = (bySection.A ?? []).slice(0, A_MAX_SEATS);
  for (let i = 0; i < aSeats.length; i += 1) {
    const item = aSeats[i];
    seatCoordinates.push(roundTableSeatCoordinates(item.seat.id, item.seat.name, item.number, i, SECTION_ORIGIN.A));
  }

  const bSeats = (bySection.B ?? []).slice(0, B_MAX_SEATS);
  for (let i = 0; i < bSeats.length; i += 1) {
    const item = bSeats[i];
    seatCoordinates.push(
      gridSeatCoordinates(
        item.seat.id,
        item.seat.name,
        "B",
        item.number,
        i,
        B_COLS,
        SECTION_ORIGIN.B,
        "circle",
        undefined,
        B_ROW_PITCH
      )
    );
  }

  const otherSections = Object.keys(bySection).filter((section) => !["S", "A", "B"].includes(section));
  for (const section of otherSections) {
    const origin = SECTION_ORIGIN[section] ?? { x: SECTION_ORIGIN.B.x + SECTION_GAP_X, y: 20 };
    const sectionSeats = bySection[section];
    for (let i = 0; i < sectionSeats.length; i += 1) {
      const item = sectionSeats[i];
      seatCoordinates.push(
        gridSeatCoordinates(item.seat.id, item.seat.name, section, item.number, i, 6, origin)
      );
    }
  }

  if (sSeats.length > 0) {
    tableDecorations.push(...sTableDecorations(SECTION_ORIGIN.S));
  }

  if (aSeats.length > 0) {
    tableDecorations.push(...aTableDecorations(SECTION_ORIGIN.A));
  }

  if (bSeats.length > 0) {
    tableDecorations.push(bConnectedTableDecoration(SECTION_ORIGIN.B));
  }

  const seatMaxX = Math.max(...seatCoordinates.map((s) => s.x + SEAT_WIDTH), 0);
  const seatMaxY = Math.max(...seatCoordinates.map((s) => s.y + SEAT_HEIGHT), 0);

  const tableMaxX = Math.max(
    ...tableDecorations.map((table) => {
      if (table.kind === "circle") {
        return (table.cx ?? 0) + (table.r ?? 0);
      }
      return (table.x ?? 0) + (table.width ?? 0);
    }),
    0
  );

  const tableMaxY = Math.max(
    ...tableDecorations.map((table) => {
      if (table.kind === "circle") {
        return (table.cy ?? 0) + (table.r ?? 0);
      }
      return (table.y ?? 0) + (table.height ?? 0);
    }),
    0
  );

  const canvasPadding = 32;
  const maxX = Math.max(seatMaxX, tableMaxX, 900) + canvasPadding;
  const maxY = Math.max(seatMaxY, tableMaxY, 900) + canvasPadding;

  return {
    seatCoordinates,
    tableDecorations,
    width: maxX,
    height: maxY,
  };
};

export const SEAT_SIZE = {
  WIDTH: SEAT_WIDTH,
  HEIGHT: SEAT_HEIGHT,
  GAP: SEAT_GAP,
  A_SEAT_RADIUS,
  GRID_CIRCLE_SEAT_RADIUS,
};
