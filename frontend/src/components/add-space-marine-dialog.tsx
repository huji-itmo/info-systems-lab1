"use client";

import { useMutation, useQueryClient } from "@tanstack/react-query";

import {
  DialogClose,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Checkbox } from "@/components/ui/checkbox";

import { createSpaceMarine } from "@/lib/api";
import { paths } from "@/types/api.types";
import { useForm } from "react-hook-form";
import { spaceMarineSchema } from "@/lib/schemas";
import { zodResolver } from "@hookform/resolvers/zod"
import z from "zod";

type SpaceMarineCreateRequest =
  paths["/space-marines"]["post"]["requestBody"]["content"]["application/json"];

interface AddSpaceMarineDialogProps {
  setOpen: (open: boolean) => void;
}

export function AddSpaceMarineDialogContent({ setOpen }: AddSpaceMarineDialogProps) {
  const queryClient = useQueryClient();

  const form = useForm<z.infer<typeof spaceMarineSchema>>({
    resolver: zodResolver(spaceMarineSchema),
    defaultValues: {
      name: "",
      coordinatesId: 1,
      chapterId: 1,
      health: 100,
      loyal: true,
      category: null,
      weaponType: "BOLTGUN",
    },
  });

  const mutation = useMutation({
    mutationFn: createSpaceMarine,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["space-marines"] });
      setOpen(false);
      form.reset();
    },
    onError: (error) => {
      console.error("Failed to create Space Marine:", error);
    },
  });

  const onSubmit = (data: SpaceMarineCreateRequest) => {
    mutation.mutate(data);
  };

  return (

    <DialogContent className="max-w-lg">
      <DialogHeader>
        <DialogTitle>Add New Space Marine</DialogTitle>
      </DialogHeader>

      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
          <FormField
            control={form.control}
            name="name"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Name</FormLabel>
                <FormControl>
                  <Input placeholder="e.g. Gabriel Angelos" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <div className="gap-4 grid grid-cols-2">
            <FormField
              control={form.control}
              name="coordinatesId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Coordinates ID</FormLabel>
                  <FormControl>
                    <Input
                      type="number"
                      {...field}
                      value={field.value ?? ""}
                      onChange={(e) =>
                        field.onChange(e.target.valueAsNumber || 0)
                      }
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="chapterId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Chapter ID</FormLabel>
                  <FormControl>
                    <Input
                      type="number"
                      {...field}
                      value={field.value ?? ""}
                      onChange={(e) =>
                        field.onChange(e.target.valueAsNumber || 0)
                      }
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <FormField
            control={form.control}
            name="health"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Health</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value ?? ""}
                    onChange={(e) =>
                      field.onChange(e.target.valueAsNumber || 0)
                    }
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="loyal"
            render={({ field }) => (
              <FormItem className="flex flex-row items-start space-x-3 space-y-0 p-4 border rounded-md">
                <FormControl>
                  <Checkbox
                    checked={!!field.value}
                    onCheckedChange={field.onChange}
                  />
                </FormControl>
                <div className="space-y-1 leading-none">
                  <FormLabel>Loyal to the Emperor?</FormLabel>
                </div>
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="category"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Category</FormLabel>
                <Select
                  value={field.value ?? "null"}
                  onValueChange={(value) =>
                    field.onChange(value === "null" ? null : value)
                  }
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select category" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="null">None</SelectItem>
                    <SelectItem value="AGGRESSOR">Aggressor</SelectItem>
                    <SelectItem value="INCEPTOR">Inceptor</SelectItem>
                    <SelectItem value="TACTICAL">Tactical</SelectItem>
                    <SelectItem value="CHAPLAIN">Chaplain</SelectItem>
                    <SelectItem value="APOTHECARY">Apothecary</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="weaponType"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Weapon Type</FormLabel>
                <Select
                  value={field.value}
                  onValueChange={field.onChange}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select weapon" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="BOLTGUN">Boltgun</SelectItem>
                    <SelectItem value="HEAVY_BOLTGUN">Heavy Boltgun</SelectItem>
                    <SelectItem value="FLAMER">Flamer</SelectItem>
                    <SelectItem value="HEAVY_FLAMER">Heavy Flamer</SelectItem>
                    <SelectItem value="MULTI_MELTA">Multi-Melta</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />

          <div className="flex justify-end gap-2">
            <DialogClose>
              <Button
                type="button"
                variant="outline"
                onClick={() => {
                  setOpen(false);
                  form.reset();
                }}
              >
                Cancel
              </Button>
            </DialogClose>

            <Button type="submit" disabled={mutation.isPending}>
              {mutation.isPending ? "Creating..." : "Create Marine"}
            </Button>
          </div>
        </form>
      </Form>
    </DialogContent>
  );
}
