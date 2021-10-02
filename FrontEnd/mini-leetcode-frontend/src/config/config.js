export const API_URL =
  process.env.NODE_ENV === "production"
    ? "/api"
    : "http://192.168.1.100:8080/api";
