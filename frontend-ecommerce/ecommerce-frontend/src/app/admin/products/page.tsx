/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";

import { useEffect, useState } from "react";
import {
  Box,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import {
  DndContext,
  closestCenter,
  PointerSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  SortableContext,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import { arrayMove } from "@dnd-kit/sortable";
import DraggableRow from "@/Components/DraggableRow";
import { useRouter } from "next/navigation"; // ✅ correct import

type Category = {
  id: number;
  title: string;
  description: string | null;
  sortOrder: number;
};

export type Product = {
  id: number;
  title: string;
  description: string;
  price: number;
  category: Category;
  sortOrder: number;
};

export default function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const router = useRouter(); // ✅ use the hook, not the default import

  const fetchProducts = async () => {
    const res = await fetch("http://localhost:8080/api/products", {
      credentials: "include",
    });
    const data = await res.json();
    setProducts(data.sort((a: Product, b: Product) => a.sortOrder - b.sortOrder));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDelete = async (id: number) => {
    await fetch(`http://localhost:8080/api/products/admin/${id}`, {
      method: "DELETE",
      credentials: "include",
    });
    setProducts(prev => prev.filter(product => product.id !== id));
  };

  const handleEdit = (id: number) => {
    router.push(`/admin/products/edit/${id}`);
  };

  const sensors = useSensors(useSensor(PointerSensor));

  const handleDragEnd = async (event: any) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    const oldIndex = products.findIndex(p => p.id === active.id);
    const newIndex = products.findIndex(p => p.id === over.id);

    const reordered = arrayMove(products, oldIndex, newIndex);
    setProducts(reordered);

    await fetch("http://localhost:8080/api/products/admin/reorder", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(reordered.map((p, i) => ({ id: p.id, sort_order: i }))),
    });
  };

  return (
    <Box p={4}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Manage Products</Typography>
        <Button variant="contained" color="primary" href="/admin/products/add">
          Add Product
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
          <SortableContext items={products.map(p => p.id)} strategy={verticalListSortingStrategy}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell><strong>ID</strong></TableCell>
                  <TableCell><strong>Title</strong></TableCell>
                  <TableCell><strong>Description</strong></TableCell>
                  <TableCell><strong>Price</strong></TableCell>
                  <TableCell><strong>Category</strong></TableCell>
                  <TableCell><strong>Sort Order</strong></TableCell>
                  <TableCell align="right"><strong>Actions</strong></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {products.map(product => (
                  <DraggableRow
                    key={product.id}
                    product={product}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                  />
                ))}
              </TableBody>
            </Table>
          </SortableContext>
        </DndContext>
      </TableContainer>
    </Box>
  );
}
