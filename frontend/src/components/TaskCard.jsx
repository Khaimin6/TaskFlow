import { Trash2, Edit, Calendar, AlertCircle } from 'lucide-react';

const TaskCard = ({ task, onEdit, onDelete }) => {
    const priorityColors = {
        HIGH: 'bg-red-100 text-red-700',
        MEDIUM: 'bg-yellow-100 text-yellow-700',
        LOW: 'bg-blue-100 text-blue-700',
    };

    const statusColors = {
        TODO: 'bg-gray-100 text-gray-700',
        IN_PROGRESS: 'bg-indigo-100 text-indigo-700',
        DONE: 'bg-green-100 text-green-700',
    };

    return (
        <div className="bg-white p-5 rounded-lg shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
            <div className="flex justify-between items-start mb-3">
                <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${priorityColors[task.priority] || 'bg-gray-100'}`}>
                    {task.priority}
                </span>
                <div className="flex gap-2">
                    <button onClick={() => onEdit(task)} className="text-gray-400 hover:text-blue-600 transition-colors">
                        <Edit className="h-4 w-4" />
                    </button>
                    <button onClick={() => onDelete(task.id)} className="text-gray-400 hover:text-red-600 transition-colors">
                        <Trash2 className="h-4 w-4" />
                    </button>
                </div>
            </div>

            <h3 className="text-lg font-semibold text-gray-900 mb-2">{task.title}</h3>
            <p className="text-gray-600 text-sm mb-4 line-clamp-2">{task.description}</p>

            <div className="flex items-center justify-between mt-auto">
                <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${statusColors[task.status]}`}>
                    {task.status.replace('_', ' ')}
                </span>
                {task.dueDate && (
                    <div className="flex items-center text-xs text-gray-500">
                        <Calendar className="h-3 w-3 mr-1" />
                        {new Date(task.dueDate).toLocaleDateString()}
                    </div>
                )}
            </div>
        </div>
    );
};

export default TaskCard;
