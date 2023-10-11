from fastapi import FastAPI
import os

app = FastAPI()


@app.get("/")
def read_root():
    port = os.getenv("PORT") or 4000
    return f"Running on port {port}"
