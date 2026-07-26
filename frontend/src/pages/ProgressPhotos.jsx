import { useEffect, useState, useRef } from "react";
import { Plus, Trash2 } from "lucide-react";
import toast from "react-hot-toast";
import { photoApi } from "../api/endpoints";
import { useAuth } from "../context/AuthContext";
import PageHeader from "../components/PageHeader";
import Loader from "../components/Loader";
import EmptyState from "../components/EmptyState";
import { fmtDate, today } from "../utils/format";

export default function ProgressPhotos() {
  const { user } = useAuth();
  const [photos, setPhotos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    description: "",
    recordDate: today(),
  });

  const fileInputRef = useRef(null);
  const [selectedFile, setSelectedFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      setPhotos((await photoApi.list(user.id)) || []);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    if (user?.id) {
      load();
    }
  }, [user?.id]);
  useEffect(() => {
    return () => {
      if (preview) {
        URL.revokeObjectURL(preview);
      }
    };
  }, [preview]);

  const submit = async (e) => {
    e.preventDefault();

    if (!selectedFile) {
      toast.error("Please select an image.");
      return;
    }

    setBusy(true);

    try {
      const formData = new FormData();

      formData.append("file", selectedFile);
      formData.append("description", form.description);
      formData.append("recordDate", form.recordDate);

      await photoApi.save(user.id, formData);

      toast.success("Photo uploaded successfully.");

      setForm({
        description: "",
        recordDate: today(),
      });

      setSelectedFile(null);
      setPreview(null);

      if (fileInputRef.current) {
        fileInputRef.current.value = "";
      }

      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || "Upload failed");
    } finally {
      setBusy(false);
    }
  };

  const remove = async (id) => {
    if (!confirm("Delete this progress photo?")) return;

    try {
      await photoApi.delete(id);

      toast.success("Photo deleted successfully.");

      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || "Failed to delete photo.");
    }
  };

  return (
    <div>
      <PageHeader
        title="Progress Photos"
        subtitle="A visual diary of who you're becoming."
      />

      <form
        onSubmit={submit}
        className="card mb-8 grid gap-3 md:grid-cols-[2fr,1fr,auto]"
        data-testid="photo-form"
      >
        <div>
          <label className="label">Choose Progress Photo</label>

          <input
            ref={fileInputRef}
            type="file"
            accept="image/*"
            className="input"
            required
            onChange={(e) => {
              const file = e.target.files[0];

              if (!file) return;

              if (!file.type.startsWith("image/")) {
                toast.error("Please select a valid image.");
                e.target.value = "";
                return;
              }

              setSelectedFile(file);
              setPreview(URL.createObjectURL(file));
            }}
          />
        </div>
        <div>
          <label className="label">Date</label>
          <input
            type="date"
            className="input"
            value={form.recordDate}
            onChange={(e) => setForm({ ...form, recordDate: e.target.value })}
          />
        </div>
        <div className="flex items-end">
          <button
            type="submit"
            disabled={busy}
            className="btn-primary w-full"
            data-testid="photo-submit"
          >
            <Plus className="h-4 w-4" /> Add
          </button>
        </div>
        <div className="md:col-span-3">
          <label className="label">Description (optional)</label>
          <input
            className="input"
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
          />
          {preview && (
            <div className="md:col-span-3">
              <label className="label">Preview</label>

              <img
                src={preview}
                alt="Preview"
                className="mt-3 w-72 rounded-2xl border border-cream-200 shadow-soft"
              />
            </div>
          )}
        </div>
      </form>

      {loading ? (
        <Loader />
      ) : photos.length === 0 ? (
        <EmptyState
          title="No photos yet"
          hint="Add your first snapshot to start a visual timeline."
        />
      ) : (
        <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {photos.map((p) => (
            <div
              key={p.id}
              className="group overflow-hidden rounded-3xl border border-cream-200 bg-cream-50 shadow-soft"
              data-testid={`photo-card-${p.id}`}
            >
              <div className="relative aspect-[3/4] w-full overflow-hidden bg-cream-100">
                <img
                  src={p.imageUrl}
                  alt={p.description || "progress"}
                  className="h-full w-full object-cover transition duration-500 group-hover:scale-105"
                  onError={(e) => {
                    e.currentTarget.src =
                      "data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 200 300%22><rect width=%22200%22 height=%22300%22 fill=%22%23EAE3D0%22/><text x=%22100%22 y=%22150%22 text-anchor=%22middle%22 fill=%22%235B6250%22 font-size=%2214%22>Image unavailable</text></svg>";
                  }}
                />
              </div>
              <div className="flex items-start justify-between gap-3 p-4">
                <div>
                  <p className="text-xs uppercase tracking-[0.22em] text-ink-500">
                    {fmtDate(p.recordDate)}
                  </p>
                  {p.description && (
                    <p className="mt-1 text-sm text-ink-700">{p.description}</p>
                  )}
                </div>
                <button
                  className="btn-ghost text-clay-600"
                  onClick={() => remove(p.id)}
                >
                  <Trash2 className="h-3.5 w-3.5" />
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
