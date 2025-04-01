"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import {
  Box,
  Button,
  TextField,
  Typography,
  MenuItem,
  CircularProgress,
} from "@mui/material";

type Category = {
  id: number;
  title: string;
};

export default function EditProductPage() {
  const params = useParams();
  const router = useRouter();
  const id = Array.isArray(params.id) ? params.id[0] : params.id;

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    if (!id) return;

    const fetchData = async () => {
      try {
        const productRes = await fetch(`http://localhost:8080/api/products/${id}`, {
          credentials: "include",
        });

        if (!productRes.ok) throw new Error("Failed to load product");
        const productData = await productRes.json();

        setTitle(productData.title);
        setDescription(productData.description);
        setPrice(productData.price.toString());
        setCategoryId(productData.category.id.toString());

        const categoryRes = await fetch("http://localhost:8080/api/categories", {
          credentials: "include",
        });
        const categoryData = await categoryRes.json();
        setCategories(categoryData);

        setLoading(false);
      } catch (err) {
        console.error("Error loading product:", err);
        setError(true);
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async () => {
    const updated = {
      title,
      description,
      price: parseFloat(price),
      category: { id: parseInt(categoryId) },
    };

    try {
      const res = await fetch(`http://localhost:8080/api/products/admin/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(updated),
      });

      if (!res.ok) {
        const message = await res.text();
        throw new Error(message || "Failed to update product");
      }

      router.push("/admin/products");
    } catch (err) {
      console.error("Failed to update:", err);
      alert("Failed to update product.");
    }
  };

  if (loading) {
    return (
      <Box mt={10} textAlign="center">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box mt={10} textAlign="center">
        <Typography color="error">Could not load product.</Typography>
      </Box>
    );
  }

  return (
    <Box maxWidth={600} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Edit Product</Typography>
      <TextField
        label="Title"
        fullWidth
        value={title}
        onChange={e => setTitle(e.target.value)}
        margin="normal"
      />
      <TextField
        label="Description"
        fullWidth
        multiline
        rows={3}
        value={description}
        onChange={e => setDescription(e.target.value)}
        margin="normal"
      />
      <TextField
        label="Price"
        fullWidth
        type="number"
        value={price}
        onChange={e => setPrice(e.target.value)}
        margin="normal"
      />
      <TextField
        label="Category"
        select
        fullWidth
        value={categoryId}
        onChange={e => setCategoryId(e.target.value)}
        margin="normal"
      >
        {categories.map(cat => (
          <MenuItem key={cat.id} value={cat.id}>
            {cat.title}
          </MenuItem>
        ))}
      </TextField>
      <Button
        variant="contained"
        color="primary"
        onClick={handleSubmit}
        sx={{ mt: 2 }}
      >
        Save Changes
      </Button>
    </Box>
  );
}
