

"use client";

import { useEffect, useState } from "react";
import {
  Box,
  Grid,
  Card,
  CardContent,
  Typography,
  Button,
  CardActions,
  IconButton,
  Divider,
} from "@mui/material";
import { Delete } from "lucide-react";

type Category = {
  id: number;
  title: string;
};

type Product = {
  id: number;
  title: string;
  description: string;
  price: number;
  category: Category;
};

type CartItem = {
  id: number;
  product: Product;
  quantity: number;
};

export default function UserDashboard() {
  const [products, setProducts] = useState<Product[]>([]);
  const [cart, setCart] = useState<CartItem[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/products", { credentials: "include" })
      .then(res => res.json())
      .then(setProducts);

    fetchCart();
  }, []);

  const fetchCart = async () => {
    const res = await fetch("http://localhost:8080/api/cart", {
      credentials: "include",
    });
    const data = await res.json();
    setCart(data);
  };

  const handleAddToCart = async (productId: number) => {
    await fetch("http://localhost:8080/api/cart/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ productId, quantity: 1 }),
    });
    fetchCart();
  };

  const handleRemoveFromCart = async (productId: number) => {
    await fetch("http://localhost:8080/api/cart/remove", {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ productId }),
    });
    fetchCart();
  };

  const handleOrder = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/orders/place", {
        method: "POST",
        credentials: "include",
      });

      if (!res.ok) {
        const message = await res.text();
        throw new Error(message || "Failed to place order");
      }

      const order = await res.json();
      alert(`✅ Order placed! Total: $${order.totalAmount}`);
      fetchCart();
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (err: any) {
      console.error("Order error:", err);
      if (err.message.includes("Cart is empty")) {
        alert("🛒 Your cart is empty. Please add products to your cart first.");
      } else {
        alert("❌ Failed to place order.");
      }
    }
  };

  const getCartItem = (productId: number) =>
    cart.find(item => item.product.id === productId);

  const groupedByCategory = products.reduce((acc, product) => {
    const cat = product.category?.title || "Uncategorized";
    if (!acc[cat]) acc[cat] = [];
    acc[cat].push(product);
    return acc;
  }, {} as Record<string, Product[]>);

  return (
    <Box p={4}>
      <Typography variant="h4" gutterBottom>
        User Dashboard
      </Typography>

      {Object.entries(groupedByCategory).map(([category, products]) => (
        <Box key={category} mt={4}>
          <Typography variant="h5" gutterBottom>
            📚 {category}
          </Typography>

          <Grid container spacing={3}>
            {products.map(product => {
              const item = getCartItem(product.id);

              return (
                <Grid item key={product.id} xs={12} sm={4} md={4}>
                <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                  <CardContent>
                    <Typography variant="h6">{product.title}</Typography>
                    <Typography variant="body2">{product.description}</Typography>
                    <Typography>${product.price.toFixed(2)}</Typography>
                  </CardContent>
                  <CardActions>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => handleAddToCart(product.id)}
                    >
                      Add to Cart
                    </Button>
                    <Button
                      variant="contained"
                      color="success"
                      onClick={handleOrder}
                    >
                      Order
                    </Button>
                  </CardActions>
                  {item && (
                    <Box px={2} py={1} display="flex" alignItems="center" justifyContent="space-between">
                      <Typography variant="body2">
                        In Cart: {item.quantity}
                      </Typography>
                      <IconButton
                        color="error"
                        onClick={() => handleRemoveFromCart(item.product.id)}
                      >
                        <Delete />
                      </IconButton>
                    </Box>
                  )}
                </Card>
              </Grid>
              
              );
            })}
          </Grid>

          <Divider sx={{ my: 4 }} />
        </Box>
      ))}
    </Box>
  );
}
