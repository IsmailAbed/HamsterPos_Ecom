
// fetch from this api , whether the logged in is user or admin
export async function getUserRole(): Promise<"ADMIN" | "USER" | null> {
  try {
    const res = await fetch("http://localhost:8080/api/user/role", {
      credentials: "include",// to send cookies/session info.
    });
    if (!res.ok) return null;
    const data = await res.text(); // plain string like "ADMIN" or "USER"
    return data === "ADMIN" ? "ADMIN" : "USER";
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  } catch (e) {
    return null;
  }
}

  