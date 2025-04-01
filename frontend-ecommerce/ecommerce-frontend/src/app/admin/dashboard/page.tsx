"use client";

import { Button } from "@mui/material";

export default function AdminDashboard() {
  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Hello Admin 👋</h1>

      <div className="flex space-x-16">
        <Button variant="contained" color="primary" href="/admin/products">
          Manage Products
        </Button>
        <Button variant="contained" color="secondary" href="/admin/categories">
          Manage Categories
        </Button>
        <Button variant="outlined" color="error" href="http://localhost:8080/req/login">
          Logout
        </Button>
      </div>
    </div>
  );
}
