"use client";

import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import {
  TableRow,
  TableCell,
  IconButton,
  Tooltip,
} from "@mui/material";
import { GripVertical, Delete, Edit } from "lucide-react";
import { Category } from "@/app/admin/categories/page";

type Props = {
  category: Category;
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
};

export default function DraggableCategoryRow({ category, onDelete, onEdit }: Props) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: category.id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  return (
    <TableRow ref={setNodeRef} style={style} {...attributes}>
      <TableCell {...listeners} sx={{ cursor: "grab", width: "40px" }}>
        <GripVertical size={18} />
      </TableCell>
      <TableCell>{category.id}</TableCell>
      <TableCell>{category.title}</TableCell>
      <TableCell>{category.description ?? "—"}</TableCell>
      <TableCell>{category.sortOrder}</TableCell>
      <TableCell align="right">
        <Tooltip title="Edit Category">
          <IconButton onClick={() => onEdit(category.id)} color="primary">
            <Edit size={18} />
          </IconButton>
        </Tooltip>
        <Tooltip title="Delete Category">
          <IconButton onClick={() => onDelete(category.id)} color="error">
            <Delete size={18} />
          </IconButton>
        </Tooltip>
      </TableCell>
    </TableRow>
  );
}
