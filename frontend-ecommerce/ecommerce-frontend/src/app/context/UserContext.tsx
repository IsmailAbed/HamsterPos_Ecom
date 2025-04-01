
// allows any component in your app to access the user’s role without fetching it again
"use client";
import { createContext, useContext, useEffect, useState } from "react";
import { getUserRole } from "../lib/auth"; // from the lib/auth we created a simple fetch to know if user or adin

type Role = "ADMIN" | "USER" | null;

const UserContext = createContext<{ role: Role }>({ role: null });

export const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const [role, setRole] = useState<Role>(null); //initial role of null

  useEffect(() => {
    getUserRole()
      .then(setRole)
      .catch(() => setRole(null));
  }, []);

  return (
    //Make it available app-wide using <UserContext.Provider>
    <UserContext.Provider value={{ role }}>
      {children}
    </UserContext.Provider>
  );
};
//role-based rendering
export const useUser = () => useContext(UserContext);
