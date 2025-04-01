//SSR-style approach 


import { redirect } from "next/navigation";
import { getUserRole } from "../lib/auth";

export default async function HomePage() {
  const role = await getUserRole();

  if (role === "ADMIN") {
    redirect("/admin/products");
  } else if (role === "USER") {
    redirect("/user/dashboard");
  } else {
    redirect("/login");
  }
}
