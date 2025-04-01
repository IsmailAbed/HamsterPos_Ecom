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
import { Product } from "@/app/admin/products/page";

type Props = {
  product: Product;
  onDelete: (id: number) => void;
  onEdit: (id: number) => void;
};

export default function DraggableRow({ product, onDelete, onEdit }: Props) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: product.id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  return (
    <TableRow ref={setNodeRef} style={style} {...attributes}>
      <TableCell {...listeners} sx={{ cursor: "grab", width: "40px" }}>
        <GripVertical size={18} />
      </TableCell>
      <TableCell>{product.id}</TableCell>
      <TableCell>{product.title}</TableCell>
      <TableCell>{product.description}</TableCell>
      <TableCell>${product.price.toFixed(2)}</TableCell>
      <TableCell>{product.category?.title || "N/A"}</TableCell>
      <TableCell>{product.sortOrder}</TableCell>
      <TableCell align="right">
        <Tooltip title="Edit Product">
          <IconButton onClick={() => onEdit(product.id)} color="primary">
            <Edit size={18} />
          </IconButton>
        </Tooltip>
        <Tooltip title="Delete Product">
          <IconButton onClick={() => onDelete(product.id)} color="error">
            <Delete size={18} />
          </IconButton>
        </Tooltip>
      </TableCell>
    </TableRow>
  );
}
