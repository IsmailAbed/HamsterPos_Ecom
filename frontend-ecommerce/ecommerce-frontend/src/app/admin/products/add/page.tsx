"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import {
  Box,
  Button,
  MenuItem,
  TextField,
  Typography,
} from "@mui/material";

type Category = {
  id: number;
  title: string;
};

export default function AddProductPage() {
  const router = useRouter();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/categories", { credentials: "include" })
      .then(res => res.json())
      .then(data => setCategories(data));
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // const product = {
    //   title,
    //   description,
    //   price: parseFloat(price),
    //   categoryId: parseInt(categoryId),
    // };

    const response = await fetch("http://localhost:8080/api/products/admin", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      credentials: "include", // This sends the session cookie
      body: JSON.stringify({
        title,
        description,
        price: parseFloat(price),
        sortOrder: 0, // optional or default
        category: {
          id: parseInt(categoryId), // Important: nested object with ID
        },
      }),
    });
    

    if (response.ok) {
      router.push("/admin/products"); // Redirect back to product list
    } else {
      alert("Failed to add product");
    }
  };

  return (
    <Box p={4} maxWidth={600} mx="auto">
      <Typography variant="h4" mb={3}>
        Add New Product
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Title"
          fullWidth
          required
          value={title}
          onChange={e => setTitle(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Description"
          fullWidth
          multiline
          rows={4}
          value={description}
          onChange={e => setDescription(e.target.value)}
          margin="normal"
        />
        <TextField
          label="Price"
          fullWidth
          required
          type="number"
          value={price}
          onChange={e => setPrice(e.target.value)}
          margin="normal"
        />
        <TextField
          select
          fullWidth
          required
          label="Category"
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
        <Button variant="contained" color="primary" type="submit" sx={{ mt: 2 }}>
          Submit
        </Button>
      </form>
    </Box>
  );
}
