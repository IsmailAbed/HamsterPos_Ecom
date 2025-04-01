// csr aproach (slower)

// "use client";

// import { useEffect, useState } from "react";
// import { useRouter } from "next/navigation";
// import { getUserRole } from "./lib/auth";

// export default function IndexPage() {
//   // eslint-disable-next-line @typescript-eslint/no-unused-vars
//   const [role, setRole] = useState<"ADMIN" | "USER" | null>(null);
//   const router = useRouter();

//   useEffect(() => {
//     getUserRole().then((fetchedRole) => {
//       console.log("🔐 Fetched role:", fetchedRole);
//       setRole(fetchedRole);

//       if (fetchedRole === "ADMIN") {
//         router.push("/admin/dashboard");
//       } else if (fetchedRole === "USER") {
//         router.push("/user/dasboard");
//       } else {
//         router.push("/login"); // fallback
//       }
//     });
//   }, []);

//   return <p>Checking your role...</p>;
// }



// ssr: faster
import { redirect } from "next/navigation";

export default async function HomePage() {
  const res = await fetch("http://localhost:8080/api/user/role", {
    credentials: "include",
    cache: "no-store",
  });

  if (!res.ok) return redirect("/login");

  const role = await res.text();
  if (role === "ADMIN") redirect("/admin/dashboard");
  else if (role === "USER") redirect("/user/dashboard");
  else redirect("/login");
}
