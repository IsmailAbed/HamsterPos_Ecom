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
  arrayMove,
} from "@dnd-kit/sortable";
import DraggableCategoryRow from "@/Components/DraggableCategoryRow";
import { useRouter } from "next/navigation";

export type Category = {
  id: number;
  title: string;
  description: string | null;
  sortOrder: number;
};

export default function ManageCategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const router = useRouter();

  const fetchCategories = async () => {
    const res = await fetch("http://localhost:8080/api/categories", {
      credentials: "include",
    });
    const data = await res.json();
    setCategories(data.sort((a: Category, b: Category) => a.sortOrder - b.sortOrder));
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleDelete = async (id: number) => {
    try {
      const res = await fetch(`http://localhost:8080/api/categories/admin/${id}`, {
        method: "DELETE",
        credentials: "include",
      });
  
      if (!res.ok) {
        const message = await res.text();
        throw new Error(message);
      }
  
      setCategories(prev => prev.filter(category => category.id !== id));
    } catch (error: any) {
      console.error("Delete error:", error);
      alert(error.message || "❌ Failed to delete category.");
    }
  };
  

  const handleEdit = (id: number) => {
    router.push(`/admin/categories/edit/${id}`);
  };

  const sensors = useSensors(useSensor(PointerSensor));

  const handleDragEnd = async (event: any) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    const oldIndex = categories.findIndex(c => c.id === active.id);
    const newIndex = categories.findIndex(c => c.id === over.id);

    const reordered = arrayMove(categories, oldIndex, newIndex);
    setCategories(reordered);

    await fetch("http://localhost:8080/api/categories/admin/reorder", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(reordered.map((c, i) => ({ id: c.id, sort_order: i }))),
    });
  };

  return (
    <Box p={4}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Manage Categories</Typography>
        <Button variant="contained" color="primary" href="/admin/categories/add">
          Add Category
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
          <SortableContext items={categories.map(c => c.id)} strategy={verticalListSortingStrategy}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell><strong>ID</strong></TableCell>
                  <TableCell><strong>Title</strong></TableCell>
                  <TableCell><strong>Description</strong></TableCell>
                  <TableCell><strong>Sort Order</strong></TableCell>
                  <TableCell align="right"><strong>Actions</strong></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {categories.map(category => (
                  <DraggableCategoryRow
                    key={category.id}
                    category={category}
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
