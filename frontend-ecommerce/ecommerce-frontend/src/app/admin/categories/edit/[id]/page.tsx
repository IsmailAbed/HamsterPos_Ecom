"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import {
  Box,
  Button,
  TextField,
  Typography,
  CircularProgress,
} from "@mui/material";

export default function EditCategoryPage() {
  const { id } = useParams();
  const router = useRouter();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/categories/${id}`, {
          credentials: "include",
        });

        if (!res.ok) throw new Error("Failed to load category");

        const data = await res.json();
        setTitle(data.title);
        setDescription(data.description || "");
        setLoading(false);
      } catch (err) {
        console.error(err);
        setError(true);
        setLoading(false);
      }
    };

    fetchCategory();
  }, [id]);

  const handleSubmit = async () => {
    const updated = { title, description };

    const res = await fetch(`http://localhost:8080/api/categories/admin/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(updated),
    });

    if (res.ok) {
      router.push("/admin/categories");
    } else {
      alert("Failed to update category");
    }
  };

  if (loading) {
    return <Box mt={10} textAlign="center"><CircularProgress /></Box>;
  }

  if (error) {
    return <Box mt={10} textAlign="center"><Typography color="error">Could not load category.</Typography></Box>;
  }

  return (
    <Box maxWidth={600} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Edit Category</Typography>
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
      <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
        Save Changes
      </Button>
    </Box>
  );
}
